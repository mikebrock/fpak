package org.jboss.fpak;

import org.jboss.fpak.parser.Parser;
import org.jboss.fpak.strategy.RunStrategy;

import java.io.File;
import java.util.*;

/**
 * @author Mike Brock .
 */
public class GenerationContext {
    private Map<String, Object> globals = new HashMap<String, Object>();
    private File workingDirectory = new File(".").getAbsoluteFile();
    private List<File> templates = new ArrayList<File>();

    private EnumSet<Option> options;
    private Map<String, Parser> parsers = new HashMap<String, Parser>();

    private String[] templateArgs;

    private RunStrategy runStrategy;

    public GenerationContext() {
    }

    public Map<String, Object> getGlobals() {
        return globals;
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public boolean hasOption(Option option) {
        return options != null && options.contains(option);
    }

    public void addTemplate(File file) {
        templates.add(file);
    }

    public void setOption(Option option) {
        if (options == null) {
            options = EnumSet.of(option);
        }
        else {
            options.add(option);
        }
    }

    public void unsetOption(Option option) {
        if (options != null) {
            options.remove(option);
        }
    }

    public void setParser(String name, Parser parser) {
        parsers.put(name, parser);
    }

    public void setTemplateArgs(String[] templateArgs) {
        this.templateArgs = templateArgs;

        globals.put("$arg", templateArgs);
    }

    public Map<String, Parser> getParsers() {
        return Collections.unmodifiableMap(parsers);
    }

    public List<File> getTemplates() {
        return Collections.unmodifiableList(templates);
    }

    public String[] getTemplateArgs() {
        return templateArgs;
    }

    public RunStrategy getRunStrategy() {
        return runStrategy;
    }

    public void setRunStrategy(RunStrategy runStrategy) {
        this.runStrategy = runStrategy;
    }
}
