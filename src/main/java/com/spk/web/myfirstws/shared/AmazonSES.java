package com.spk.web.myfirstws.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.spk.web.myfirstws.shared.dto.UserDto;

public class AmazonSES {

    //This address must be verified with Amazon SES.
    final String FROM = "sahar.pk88@gmail.com";

    //The Subject line for the email
    final String SUBJECT = "One last step to complete your registration with PhotoApp";

    //The HTML body for the email
    final String HTMLBODY = "<h1>Please verify your email address</h1>" +
            "<p>Thank you for registering with our mobile app. To complete registration process and be " +
            "able to log in, click on the following link: "
            //Note: When we deploy the application on the production server in Amazon, this url must be updated
            + "<a href='http://localhost:8080/email-verification-service/email-verification.html?token=$tokenValue'>" +
            "Final step to complete your registration"
            + "</a><br/><br/>"
            + "Thank you! And we are waiting for you inside!";

    //The email body for recipients with non-HTML email clients.
    final String TEXTBODY = "Please verify your email address."
            + "Thank you for registering with our mobile app. To complete registration process and be able to log in, "
            + "open then the following URL in your browser window: "
            //Note: When we deploy the application on the production server in Amazon, this url must be updated
            + "http://localhost:8080/email-verification-service/email-verification.html?token=$tokenValue"
            + "Thank you! And we are waiting for you inside!";

    public void verifyEmail(UserDto userDto) {
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                //This region must be the same as the region by which the email address has been verified in Amazon
                .withRegion(Regions.EU_CENTRAL_1).build();
        // Replace the tokenValue placeholder with the token stored in the database.
        String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        String textBodyWithToken = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                //For clients that do not support html with plain text
                                .withText(new Content().withCharset("UTF-8").withData(SUBJECT)))
                        .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);

        //The AWS SDK for Java simplifies use of AWS Services by providing a set of libraries that are consistent and familiar for Java developers.
        //Amazon SDK will send the request to Amazon SES to attempt to deliver this email
        client.sendEmail(request);
        System.out.println("Emil sent!");
    }
}