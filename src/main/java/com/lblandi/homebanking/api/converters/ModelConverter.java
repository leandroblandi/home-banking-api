package com.lblandi.homebanking.api.converters;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ModelConverter<T, Y> {
	public Set<T> convertSet(Set<Y> models) {
		return Optional.ofNullable(models).orElse(Collections.emptySet()).stream().map(this::convert)
				.collect(Collectors.toSet());
	}

	public Set<Y> unconvertSet(Set<T> models) {
		return Optional.ofNullable(models).orElse(Collections.emptySet()).stream().map(this::unconvert)
				.collect(Collectors.toSet());
	}

	public T convert(Y model) {
		if (model == null)
			return null;
		return convertToDest(model);
	}

	public Y unconvert(T model) {
		if (model == null)
			return null;
		return convertFromDest(model);
	}

	protected abstract T convertToDest(Y model);

	protected abstract Y convertFromDest(T model);

}
