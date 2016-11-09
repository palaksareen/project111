package uk.co.o2.soaclient.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/siteverify")
@Produces({MediaType.APPLICATION_JSON})
public interface CaptchaValidationResource {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    ReCaptchaResponse validateReCaptcha(ReCaptchaToken reCaptchaToken);
}

