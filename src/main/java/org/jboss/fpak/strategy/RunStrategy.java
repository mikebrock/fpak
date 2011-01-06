package org.jboss.fpak.strategy;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Definition;

/**
 * @author Mike Brock .
 */
public interface RunStrategy {
    public void doStrategy(GenerationContext context, Definition definition);
}
