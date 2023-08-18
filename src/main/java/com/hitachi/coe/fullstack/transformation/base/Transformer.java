package com.hitachi.coe.fullstack.transformation.base;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

/**
 * Converts an input class instance to an output class instance.
 * @author thovo
 *
 * @param <I> INPUT
 * @param <O> OUTPUT
 */
public interface Transformer<I, O> extends Function<I, O> {

   /**
    * Checks that this transformer can transform provided input class to output class.
    *
    * @param inputClass  {@link Class} of input
    * @param outputClass {@link Class} of output
    * @return true if this transformer can transform input to output
    */
   @SuppressWarnings("rawtypes")
   default boolean isApplicable(final Class<?> inputClass, final Class<?> outputClass) {
       final Class inputClassOfThisTransformer = (Class) ((ParameterizedType) (this.getClass().getGenericInterfaces()[0]))
           .getActualTypeArguments()[0];
       final Class outputClassOfThisTransformer = (Class) ((ParameterizedType) (this.getClass().getGenericInterfaces()[0]))
           .getActualTypeArguments()[1];
       return inputClass.equals(inputClassOfThisTransformer) && outputClass.equals(outputClassOfThisTransformer);
   }
}
