package org.jboss.fpak.model.builtin;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Part;
import org.mvel2.MVEL;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.EndWithValue;

import java.io.Serializable;

/**
 * @author Mike Brock .
 */
public class InitPart implements Part {
    private Serializable expression;
    private boolean fail = false;

    public InitPart(Serializable expression) {
        this.expression = expression;
    }

    public InitState doInit(GenerationContext context) {
        InitState state = new InitState();
        state.setExitValue(MVEL.executeExpression(expression, state, context.getGlobals()));
        return state;
    }

    public static class InitState {
        private boolean initFail = false;
        private String failMessage = "";
        private Object exitValue;

        public void fail(String message) {
            initFail = true;
            failMessage = message;
        }

        public boolean isInitFail() {
            return initFail;
        }

        public void setInitFail(boolean initFail) {
            this.initFail = initFail;
        }

        public String getFailMessage() {
            return failMessage;
        }

        public void setFailMessage(String failMessage) {
            this.failMessage = failMessage;
        }

        public Object getExitValue() {
            return exitValue;
        }

        public void setExitValue(Object exitValue) {
            this.exitValue = exitValue;
        }
    }

}
