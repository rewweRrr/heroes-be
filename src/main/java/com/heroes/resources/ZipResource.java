package com.heroes.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heroes.models.HeroDto;
import com.heroes.service.HeroService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import java.io.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Path("/zip")
public class ZipResource {

    @Inject
    HeroService heroService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("")
    @Produces("application/zip")
    public Response zipJsonData() {
        StreamingOutput stream = output -> {

            try (ZipOutputStream zos = new ZipOutputStream(output)) {
                InputStream resourceStream = getClass().getResourceAsStream("/data.json");

                if (resourceStream == null) {
                    throw new IOException("Resource not found");
                }

                ZipEntry resourceEntry = new ZipEntry("data.json");
                zos.putNextEntry(resourceEntry);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = resourceStream.read(buffer)) != -1) {
                    zos.write(buffer, 0, bytesRead);
                }
                zos.closeEntry();
                resourceStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while creating ZIP", e);
            }

        };

        return Response.ok(stream)
                .header("Content-Disposition", "attachment; filename=data.zip")
                .header("Content-Type", "application/zip")
                .build();
    }

    @GET
    @Path("/content-length")
    @Produces("application/zip")
    public Response zipJsonDataContentLength() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zos = new ZipOutputStream(byteArrayOutputStream)) {
            InputStream resourceStream = getClass().getResourceAsStream("/data.json");

            if (resourceStream == null) {
                throw new IOException("Resource not found");
            }

            ZipEntry resourceEntry = new ZipEntry("data.json");
            zos.putNextEntry(resourceEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = resourceStream.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
            zos.closeEntry();
            resourceStream.close();
        } catch (IOException e) {
            return Response.serverError().entity("Error while creating ZIP").build();
        }

        byte[] zipBytes = byteArrayOutputStream.toByteArray();
        int contentLength = zipBytes.length;

        return Response.ok(new ByteArrayInputStream(zipBytes))
                .header("Content-Disposition", "attachment; filename=data-content.zip")
                .header("Content-Length", contentLength)
                .build();
    }

    @GET
    @Path("/external-test")
    @Produces("application/zip")
    public Response downloadJsonAsZip() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("https://jsonplaceholder.typicode.com/photos");

            StreamingOutput stream = output -> {
                byte[] buffer = new byte[8192];

                InputStream jsonInputStream = target.request(MediaType.APPLICATION_JSON)
                        .get(InputStream.class);

                try (ZipOutputStream zipOS = new ZipOutputStream(output)) {
                    ZipEntry zipEntry = new ZipEntry("data.json");
                    zipOS.putNextEntry(zipEntry);


                    int bytesRead;
                    while ((bytesRead = jsonInputStream.read(buffer)) != -1) {
                        zipOS.write(buffer, 0, bytesRead);
                    }

                    zipOS.closeEntry();
                } finally {
                    jsonInputStream.close();
                }
            };

            Response.ResponseBuilder response = Response.ok(stream);
            response.header("Content-Disposition", "attachment; filename=compressed_data.zip");
            response.header("Content-Type", "application/zip");

            return response.build();

        } catch (Exception e) {
            e.fillInStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/heroes")
    @Produces("application/zip")
    public Response downloadHeroesAsZip() {
        try {
            Stream<HeroDto> heroesStream = this.heroService.getHeroesAsStream();

            StreamingOutput stream = output -> {
                try (ZipOutputStream zipOS = new ZipOutputStream(new BufferedOutputStream(output))) {
                    ZipEntry zipEntry = new ZipEntry("heroes.json");
                    zipOS.putNextEntry(zipEntry);

                    for (HeroDto hero : (Iterable<HeroDto>) heroesStream::iterator) {
                        String heroJson = objectMapper.writeValueAsString(hero);
                        zipOS.write(heroJson.getBytes());
                        zipOS.write('\n');
                    }
                    zipOS.closeEntry();
                }
            };

            Response.ResponseBuilder response = Response.ok(stream);
            response.header("Content-Disposition", "attachment; filename=heroes.zip");
            response.header("Content-Type", "application/zip");

            return response.build();

        } catch (Exception e) {
            e.fillInStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}