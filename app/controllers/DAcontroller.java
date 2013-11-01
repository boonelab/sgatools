package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import models.DAjob;
import models.IAjob;
import models.NSjob;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;

import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.da.dasummary;

public class DAcontroller extends Controller {
    final static Form<DAjob> daform = form(DAjob.class);

    public static Result initPreloadedDA(String jobid) {
        NSjob nsjob = NScontroller.getJobFromJsonFile(jobid);
        IAjob iajob = IAcontroller.getJobFromJsonFile(jobid);
        Form<DAjob> dajob = daform.fill(new DAjob());

        return ok(dasummary.render(iajob, nsjob, dajob));
    }

    public static Result submit() throws IOException {
        DAjob job = new DAjob(request().body().asMultipartFormData());
        File svgFile = File.createTempFile("download", ".svg");
        
        try {
            Logger.info("writing svg to file: " + svgFile.getPath());
            BufferedWriter out = new BufferedWriter(new FileWriter(svgFile.getPath()));
            out.write(job.inputHmap);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error(e.getMessage());
            return TODO;
        }

        if (!job.saveType.equals("SVG")) {
            // Convert the SVG into PDF
            File outputFile = null;
            try {
                outputFile = File.createTempFile("result-", "." + job.saveType.toLowerCase());
                SVGConverter converter = new SVGConverter();
                if (job.saveType.equals("PDF"))
                    converter.setDestinationType(DestinationType.PDF);
                if (job.saveType.equals("PNG"))
                    converter.setDestinationType(DestinationType.PNG);
                converter.setWidth(1000);
                converter.setSources(new String[] { svgFile.toString() });
                converter.setDst(outputFile);
                converter.execute();
            } catch (Exception e) {
                e.printStackTrace();
                Logger.info("FAILED");
            }
            return ok(outputFile);
            // .as("image/x-png");
        }

        return ok(svgFile);
    }
}
