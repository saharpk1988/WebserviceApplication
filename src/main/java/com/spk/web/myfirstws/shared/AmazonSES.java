package com.spk.web.myfirstws.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.spk.web.myfirstws.shared.dto.UserDto;

public class AmazonSES {

    //This address must be verified with Amazon SES.
    final String FROM = "sahar.pk88@gmail.com";

    //The Subject line for the verification email
    final String SUBJECT = "One last step to complete your registration with PhotoApp";

    //The Subject line for the password reset email
    final String PASSWORD_RESET_SUBJECT = "Password reset request";
    //The HTML body for the verification email
    final String HTMLBODY = "<h1>Please verify your email address</h1>" +
            "<p>Thank you for registering with our mobile app. To complete registration process and be " +
            "able to log in, click on the following link: "
            + "<br/><br/>"
            //Note: When we deploy the application on the production server in Amazon, this url must be updated
            + "<a href='http://ec2-54-93-208-152.eu-central-1.compute.amazonaws.com:8080/email-verification-service/email-verification.html?token=$tokenValue'>" +
            "Final step to complete your registration"
            + "</a><br/><br/>"
            + "Thank you! And we are waiting for you inside!";
    //The verification email body for recipients with non-HTML email clients.
    final String TEXTBODY = "Please verify your email address."
            + "Thank you for registering with our mobile app. To complete registration process and be able to log in, "
            + "open then the following URL in your browser window: "
            //Note: When we deploy the application on the production server in Amazon, this url must be updated
            + "http://ec2-54-93-208-152.eu-central-1.compute.amazonaws.com:8080/email-verification-service/email-verification.html?token=$tokenValue"
            + "Thank you! And we are waiting for you inside!";
    //The HTML body for the password reset email, the clients that support html.
    final String PASSWORD_RESET_HTMLBODY = "<h1>A request to reset your password</h1>"
            + "<br/><p>Hi, $firstName!</p>"
            + "<br/><p>Someone has requested to reset your password with our project. If it were not you, please ignore this email "
            + "otherwise please click on the link below to set a new password: "
            + "<br/><br/>"
            + "<a href='http://ec2-54-93-208-152.eu-central-1.compute.amazonaws.com:8080/email-verification-service/password-reset.html?token=$tokenValue'>"
            + "Click this link to reset password"
            + "</a><br/><br/>"
            + "Thank you!";
    //The password reset email body for recipients with non-HTML email clients.
    final String PASSWORD_RESET_TEXTBODY = "A request to reset your password" +
            "Hi, $firstName!"
            + "Someone has requested to reset your password with our project. If it were not you, please ignore this email "
            + "otherwise please open the link below in your browser to set a new password: "
            + "http://ec2-54-93-208-152.eu-central-1.compute.amazonaws.com:8080/email-verification-service/mpassword-reset.html?token=$tokenValue"
            + "Thank you!";

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
        //System.out.println("Emil sent!");
    }


    public boolean sendPasswordResetRequest(String firstName, String email, String token) {
        boolean returnValue = false;

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1).build();

        String htmlBodyWithToken = PASSWORD_RESET_HTMLBODY.replace("$tokenValue", token);
        htmlBodyWithToken = htmlBodyWithToken.replace("$firstName", firstName);

        String textBodyWithToken = PASSWORD_RESET_TEXTBODY.replace("$tokenValue", token);
        textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(email))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content().withCharset("UTF-8").withData(PASSWORD_RESET_SUBJECT)))
                .withSource(FROM);

        SendEmailResult emailResult = client.sendEmail(request);
        if (emailResult != null && (emailResult.getMessageId() != null && !emailResult.getMessageId().isEmpty())) {
            returnValue = true;
        }
        return returnValue;
    }
}
