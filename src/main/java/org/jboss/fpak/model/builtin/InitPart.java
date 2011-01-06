package org.jboss.fpak.model.builtin;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Part;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;

import java.io.Serializable;

/**
 * @author Mike Brock .
 */
public class InitPart implements Part {
    private Serializable expression;

    public InitPart(Serializable expression) {
        this.expression = expression;
    }

    public void doInit(GenerationContext context) {
        MVEL.executeExpression(expression, this, context.getGlobals());
    }

    public void fail(String error) {
        throw new RuntimeException(error);
    }
}
