package com.luminoso.communications.models.entities

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Types


class PostgreSQLEnumType : org.hibernate.type.EnumType<Enum<*>>() {

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(
        st: PreparedStatement,
        value: Any?,
        index: Int,
        session: SharedSessionContractImplementor) {
        if (value == null) {
            st.setNull(index, Types.OTHER)
        } else {
            st.setObject(
                index,
                value.toString(),
                Types.OTHER
            )
        }
    }
}
