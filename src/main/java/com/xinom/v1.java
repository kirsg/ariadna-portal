package com.xinom;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.markup.html.form.ListChoice;
import java.util.Arrays;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;

public class v1 extends WebPage {
    private static final long serialVersionUID = 1L;

    private List<String> VideoFiles;
    private FileUploadField fileUpload;
    private String UPLOAD_FOLDER = "/opt/video/";


    public v1(final PageParameters parameters) {
        super(parameters);

        add(new FeedbackPanel("feedback"));

        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {

                final FileUpload uploadedFile = fileUpload.getFileUpload();
                if (uploadedFile != null) {


                    // write to a new file
                    File newFile = new File(UPLOAD_FOLDER
                            + uploadedFile.getClientFileName());

                    if (newFile.exists()) {
                        newFile.delete();
                    }

                    try {
                        newFile.createNewFile();
                        uploadedFile.writeTo(newFile);

                        info("saved file: " + uploadedFile.getClientFileName());
                    } catch (Exception e) {
                        throw new IllegalStateException("Error");
                    }
                }

            }

        };

        form.setMultiPart(true);

        // max upload size, 10k
        form.setMaxSize(Bytes.gigabytes(2));

        form.add(fileUpload = new FileUploadField("fileUpload"));

        add(form);


    }
}
