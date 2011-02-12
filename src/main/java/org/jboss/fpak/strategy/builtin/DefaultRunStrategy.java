package org.jboss.fpak.strategy.builtin;

import org.apache.commons.cli.*;
import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Definition;
import org.jboss.fpak.model.DefinitionPart;
import org.jboss.fpak.model.Parm;
import org.jboss.fpak.model.Part;
import org.jboss.fpak.model.builtin.FilePart;
import org.jboss.fpak.model.builtin.InitPart;
import org.jboss.fpak.model.builtin.InputsPart;
import org.jboss.fpak.strategy.RunStrategy;
import org.mvel2.DataConversion;
import org.mvel2.MVEL;

import java.util.List;

import static org.mvel2.DataConversion.convert;

/**
 * @author Mike Brock .
 */
public class DefaultRunStrategy implements RunStrategy {
    public void doStrategy(GenerationContext context, Definition definition) {

        InputsPart inputPart = (InputsPart) definition.getSinglePart(DefinitionPart.Parameters);

        if (inputPart != null) {
            Options option = inputPart.getOptions();
            CommandLineParser parser = new PosixParser();
            try {
                CommandLine cmd = parser.parse(option, context.getTemplateArgs());

                context.getGlobals().clear();


                for (Parm p : inputPart.getParts()) {
                    if (Boolean.class.isAssignableFrom(p.getType())) {
                        context.getGlobals().put(p.getName(), cmd.hasOption(p.getName()));

                    } else {
                        context.getGlobals().put(p.getName(), convert(cmd.getOptionValue(p.getName()), p.getType()));
                    }
                }

                context.getGlobals().put("$arg", cmd.getArgs());
                context.getGlobals().put("_out", System.out);


            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        InitPart initPart = (InitPart) definition.getSinglePart(DefinitionPart.Initializer);

        if (initPart != null) {
            InitPart.InitState state = initPart.doInit(context);

            if (state.isInitFail()) {
                MVEL.eval("_out.println(failMessage)", state, context.getGlobals());
                return;
            }
        }

        List<Part> files = definition.getPart(DefinitionPart.File);
        for (Part p : files) {
            FilePart fp = (FilePart) p;
            try {
                fp.generate(context);
            } catch (Exception e) {
                throw new RuntimeException("unknown exception", e);
            }
        }
    }
}
