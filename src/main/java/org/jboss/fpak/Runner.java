package org.jboss.fpak;

import org.jboss.fpak.model.Definition;
import org.jboss.fpak.strategy.ParseStrategy;
import org.jboss.fpak.strategy.RunStrategy;
import org.jboss.fpak.strategy.builtin.DefaultParseStrategy;
import org.jboss.fpak.strategy.builtin.DefaultRunStrategy;

import java.io.File;

/**
 * @author Mike Brock .
 */
public class Runner {
    public static final String DEFAULT_FILE_EXTENSION = ".fpk";

    private ParseStrategy parseStrategy;
    private RunStrategy runStrategy;

    private File workingDirectory = new File(".").getAbsoluteFile();

    public Runner() {
        parseStrategy = new DefaultParseStrategy();
        runStrategy = new DefaultRunStrategy();
    }

    public Runner(ParseStrategy parseStrategy, RunStrategy runStrategy) {
        this.parseStrategy = parseStrategy;
        this.runStrategy = runStrategy;
    }

    public void setWorkingDirectory(String file) {
        setWorkingDirectory(new File(file).getAbsoluteFile());
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void run(String... argv) {
        GenerationContext ctx = new GenerationContext();
        ctx.setWorkingDirectory(workingDirectory);

        System.out.println("Working in: " + workingDirectory.getAbsolutePath());

        if (argv.length == 0) {
            throw new RuntimeException("no template specified");
        }

        String firstArg = argv[0];

        if (firstArg.indexOf('.') == -1) {
            firstArg += DEFAULT_FILE_EXTENSION;
        }

        File templateFile = new File(firstArg).getAbsoluteFile();
        if (!templateFile.exists()) {
            throw new RuntimeException("template does not exist: " + templateFile.getAbsolutePath());
        }

        ctx.addTemplate(templateFile);

        String[] args = new String[argv.length - 1];
        System.arraycopy(argv, 1, args, 0, argv.length - 1);

        ctx.setTemplateArgs(args);

        Definition d = parseStrategy.doStrategy(ctx);
        runStrategy.doStrategy(ctx, d);
    }

    public static void main(String[] args) {
        new Runner().run(args);
    }
}
