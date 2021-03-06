/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.expression.function;

import org.elasticsearch.xpack.ql.expression.Expression;
import org.elasticsearch.xpack.ql.expression.function.Function;
import org.elasticsearch.xpack.ql.expression.gen.pipeline.Pipe;
import org.elasticsearch.xpack.ql.expression.gen.script.ScriptTemplate;
import org.elasticsearch.xpack.ql.tree.NodeInfo;
import org.elasticsearch.xpack.ql.tree.Source;
import org.elasticsearch.xpack.ql.type.DataType;
import org.elasticsearch.xpack.ql.type.DataTypes;
import org.elasticsearch.xpack.sql.SqlIllegalArgumentException;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Function referring to the {@code _score} in a search. Only available
 * in the search context, and only at the "root" so it can't be combined
 * with other function.
 */
public class Score extends Function {
    public Score(Source source) {
        super(source, emptyList());
    }

    @Override
    protected NodeInfo<Score> info() {
        return NodeInfo.create(this);
    }

    @Override
    public Expression replaceChildren(List<Expression> newChildren) {
        throw new UnsupportedOperationException("this type of node doesn't have any children to replace");
    }

    @Override
    public DataType dataType() {
        return DataTypes.FLOAT;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Score other = (Score) obj;
        return source().equals(other.source());
    }

    @Override
    public int hashCode() {
        return source().hashCode();
    }

    @Override
    protected Pipe makePipe() {
        throw new SqlIllegalArgumentException("Scoring cannot be computed on the client");
    }

    @Override
    public ScriptTemplate asScript() {
        throw new SqlIllegalArgumentException("Scoring cannot be scripted");
    }
}
