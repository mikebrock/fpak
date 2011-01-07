package org.jboss.fpak.model.builtin;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.Option;
import org.jboss.fpak.model.Part;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateRuntime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Mike Brock .
 */
public class FilePart implements MultiPart {
    private String pathTemplate;

    private String relativePath;
    private String fileName;

    private String template;

    public void generate(GenerationContext ctx) throws IOException {
        String path = String.valueOf(TemplateRuntime.eval(pathTemplate, ctx.getGlobals())).trim();

        if (path.indexOf('/') == -1) {
            relativePath = "";
            fileName = path;
        } else {
            relativePath = path.substring(0, path.lastIndexOf('/'));
            fileName = path.substring(relativePath.length() + 1);
        }

        File targetDir = new File(ctx.getWorkingDirectory() + "/" + relativePath).getAbsoluteFile();

        targetDir.mkdirs();

        if (!targetDir.exists()) {
            throw new RuntimeException("failed to create path: " + targetDir.getAbsolutePath());
        }

        File targetFile = new File(targetDir.getAbsolutePath() + "/" + fileName);

        if (targetFile.exists()) {
            if (ctx.hasOption(Option.Clobber)) {
                if (!targetFile.delete()) {
                    throw new RuntimeException("failed to overwrite file: cannot delete existing file: "
                            + targetFile.getAbsolutePath());
                }
            } else {
                throw new RuntimeException("file exists: " + targetFile.getAbsolutePath());
            }
        }

        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("failed to create new file: " + targetFile.getAbsolutePath(), e);
        }

        FileOutputStream outputStream = new FileOutputStream(targetFile);
        try {
            TemplateRuntime.eval(template, ctx.getGlobals(), outputStream);
            outputStream.flush();
        } catch(Exception e) {
            System.out.println("Unable to parse: " + path + "\n---------------------");
            System.out.println(template);
            System.out.println("-------------------");
            e.printStackTrace();
        }
        finally {
            outputStream.close();
        }

        System.out.println("created: " + targetFile.getAbsolutePath());
    }

    public void setPathTemplate(String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
