package org.springframework.faces.expression;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

public abstract class ELDelegatingPropertyResolver extends PropertyResolver {

	private PropertyResolver nextResolver;

	private ELContext elContext;

	public ELDelegatingPropertyResolver(PropertyResolver nextResolver, ELResolver delegate) {
		this.nextResolver = nextResolver;
		this.elContext = new SimpleELContext(delegate);
	}

	public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		Class type = elContext.getELResolver().getType(elContext, base, new Integer(index));
		if (elContext.isPropertyResolved()) {
			return type;
		} else {
			return nextResolver.getType(base, index);
		}
	}

	public Class getType(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		Class type = elContext.getELResolver().getType(elContext, base, property);
		if (elContext.isPropertyResolved()) {
			return type;
		} else {
			return nextResolver.getType(base, property);
		}
	}

	public Object getValue(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		Object value = elContext.getELResolver().getValue(elContext, base, new Integer(index));
		if (elContext.isPropertyResolved()) {
			return value;
		} else {
			return nextResolver.getValue(base, index);
		}
	}

	public Object getValue(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		Object value = elContext.getELResolver().getValue(elContext, base, property);
		if (elContext.isPropertyResolved()) {
			return value;
		} else {
			return nextResolver.getValue(base, property);
		}
	}

	public boolean isReadOnly(Object base, int index) throws EvaluationException, PropertyNotFoundException {
		boolean readOnly = elContext.getELResolver().isReadOnly(elContext, base, new Integer(index));
		if (elContext.isPropertyResolved()) {
			return readOnly;
		} else {
			return nextResolver.isReadOnly(base, index);
		}
	}

	public boolean isReadOnly(Object base, Object property) throws EvaluationException, PropertyNotFoundException {
		boolean readOnly = elContext.getELResolver().isReadOnly(elContext, base, property);
		if (elContext.isPropertyResolved()) {
			return readOnly;
		} else {
			return nextResolver.isReadOnly(base, property);
		}
	}

	public void setValue(Object base, int index, Object value) throws EvaluationException, PropertyNotFoundException {
		elContext.getELResolver().setValue(elContext, base, new Integer(index), value);
		if (!elContext.isPropertyResolved()) {
			nextResolver.setValue(base, index, value);
		}
	}

	public void setValue(Object base, Object property, Object value) throws EvaluationException,
			PropertyNotFoundException {
		elContext.getELResolver().setValue(elContext, base, property, value);
		if (!elContext.isPropertyResolved()) {
			nextResolver.setValue(base, property, value);
		}
	}

}