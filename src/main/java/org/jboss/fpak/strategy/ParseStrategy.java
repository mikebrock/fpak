package org.jboss.fpak.strategy;

import org.jboss.fpak.GenerationContext;
import org.jboss.fpak.model.Definition;

/**
 * @author Mike Brock .
 */
public interface ParseStrategy {
    public Definition doStrategy(GenerationContext context);
}
