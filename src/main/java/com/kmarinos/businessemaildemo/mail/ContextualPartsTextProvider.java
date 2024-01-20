package com.kmarinos.businessemaildemo.mail;


import com.kmarinos.businessemaildemo.SupplierRegistry;
import com.kmarinos.businessemaildemo.exceptions.NoDeferredTextProviderException;
import com.kmarinos.businessemaildemo.exceptions.TextProviderDoesNotImplementMethodException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class ContextualPartsTextProvider<R, T extends MailContext> extends PartsTextProvider<T> implements ConditionalContextualTextProvider<R> {
    R conditionalContext;

    public ContextualPartsTextProvider(R conditionalContext) {
        this.conditionalContext = conditionalContext;
    }

    private final ThreadLocal<ContextualPartsTextProvider<R, ? extends MailContext>> referencedTextProvider = new ThreadLocal<>();

    protected <X> X defer() {

        StackTraceElement caller = findDirectCaller();
        var otherTextProvider = EmailTextProvider.standByTextProviders.get().getNext(caller.getClassName()).orElseThrow();

        return runNextAvailableHomonymousMethod(caller.getClassName(),caller.getMethodName(), otherTextProvider, new Object[]{},
                previousTextProvider -> EmailTextProvider.standByTextProviders.get().getNext(previousTextProvider.getClass().getName())
                        .orElseThrow(() -> new NoDeferredTextProviderException(caller.getClassName(), caller.getMethodName())));
    }
    protected <X> X defer(Class<? extends ContextualPartsTextProvider<R, ? extends MailContext>> otherTextProviderClass, Object... inputParams) {
        var caller = findDirectCaller();
        var otherTextProvider = SupplierRegistry.getRegisteredSupplier(otherTextProviderClass).get();
        return runNextAvailableHomonymousMethod(caller.getClassName(),caller.getMethodName(), otherTextProvider, inputParams);
    }
    protected <X,U extends MailContext> X defer(Function<U,X> method) {
        var result = (U) referencedTextProvider.get().getContext().get();
        referencedTextProvider.remove();
        return method.apply(result);
    }
    protected <X> X defer(MethodCall<R> methodCall, Object... inputParams) {
        var caller = findDirectCaller();
        var otherTextProvider = SupplierRegistry.getRegisteredSupplier(methodCall.getOtherTextProvider()).get();
        return runNextAvailableHomonymousMethod(caller.getClassName(),methodCall.getMethodName(), otherTextProvider, inputParams);
    }
    protected <U extends ContextualPartsTextProvider<R, ? extends MailContext>> U to(Class<U>textProviderClass){
        referencedTextProvider.set(SupplierRegistry.getRegisteredSupplier(textProviderClass).get());
        return (U) referencedTextProvider.get();
    }
    protected MethodCall<R> toStandard(){

        referencedTextProvider.set(SupplierRegistry.getEmailTextProvider(conditionalContext.getClass()).getFallbackTextProvider());
        return new MethodCall<>(findDirectCaller("toStandard").getMethodName(),referencedTextProvider.get().getClass());
    }
    protected MethodCall<R> toMethod(String methodName,Class<? extends ContextualPartsTextProvider<R, ? extends MailContext>> otherTextProvider){
        return new MethodCall<>(methodName,otherTextProvider);
    }
    private StackTraceElement findDirectCaller() {
        return findDirectCaller( "defer");
    }
    private StackTraceElement findDirectCaller(String fromMethod) {
        int index = 0;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//        for (int i = 0; i < stackTraceElements.length; i++) {
//            if (stackTraceElements[i].getMethodName().equals("defer")) {
//                index = i + 1;
//                break;
//            }
//        }

            for (int i = 0; i < stackTraceElements.length; i++) {
                if (stackTraceElements[i].getMethodName().equals(fromMethod)) {
                    index = i + 1;
                    break;
                }
            }

        return stackTraceElements[index];
    }
    private <X> X runNextAvailableHomonymousMethod(String callerClass,String callerMethod, ContextualPartsTextProvider otherTextProvider, Object[] inputParams, UnaryOperator<ContextualPartsTextProvider> onError) {

        var fixedInputParams = new ArrayList<>();
        var classArray = new ArrayList<Class>();
        var foundContextObject = false;
        for (int i = 0; i < inputParams.length; i++) {
            var paramClass = inputParams[i].getClass();
            var inputObject = inputParams[i];
            if (MailContext.class.isAssignableFrom(paramClass)) {
                inputObject = otherTextProvider.getContext().get(); //assign MailContext from the correct provider
                foundContextObject = true;
            }
            fixedInputParams.add(inputObject);
            classArray.add(inputObject.getClass());
        }

        Method method = null;
        try {
            method = otherTextProvider.getClass().getMethod(callerMethod, classArray.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            //retry after adding mailcontext
            if (!foundContextObject) {
                var ctx = otherTextProvider.getContext().get();
                fixedInputParams.add(0, ctx);
                classArray.add(0, ctx.getClass());
                try {
                    method = otherTextProvider.getClass().getMethod(callerMethod, classArray.toArray(new Class[0]));
                } catch (NoSuchMethodException ex) {
                    if (onError != null) {
                        return runNextAvailableHomonymousMethod(callerClass,callerMethod, onError.apply(otherTextProvider), inputParams);
                    } else {
                        throw new TextProviderDoesNotImplementMethodException(otherTextProvider.getClass().getName(),callerClass,callerMethod);
                    }
                }
            } else {
                throw new RuntimeException(e);
            }
        }
        try {
            return (X) method.invoke(otherTextProvider, fixedInputParams.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private <X> X runNextAvailableHomonymousMethod(String callerClass,String callerMethod, ContextualPartsTextProvider otherTextProvider, Object[] inputParams) {
        return runNextAvailableHomonymousMethod(callerClass,callerMethod, otherTextProvider, inputParams, null);
    }

    @Override
    public R getConditionalContext() {
        return conditionalContext;
    }

    @Override
    public StringBuilder execute(StringBuilder stringBuilder) {
        orchestrateTextParts();
        return build(stringBuilder);
    }

    protected abstract void orchestrateTextParts();

    @Data
    @AllArgsConstructor
    private static class MethodCall<R>{
        String methodName;
        Class<? extends ContextualPartsTextProvider> otherTextProvider;

    }


}
