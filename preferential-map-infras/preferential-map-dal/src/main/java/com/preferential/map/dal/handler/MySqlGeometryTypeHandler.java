/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 21:58
 **/

package com.preferential.map.dal.handler;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.WKBReader;

/*
 * mybatis查询结果集中 mysql的geometry类型映射到Point对象
 */
@MappedTypes(value = {Point.class})
public class MySqlGeometryTypeHandler extends BaseTypeHandler<Point> {

    private WKBReader wkbReader;
    private int srid = 4326;

    public MySqlGeometryTypeHandler() {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
        wkbReader = new WKBReader(geometryFactory);
    }


    @Override
    public Point getNullableResult(ResultSet arg0, String arg1) throws SQLException {
        return fromMysqlWkb(arg0.getBytes(arg1));
    }

    @Override
    public Point getNullableResult(ResultSet arg0, int arg1) throws SQLException {
        return fromMysqlWkb(arg0.getBytes(arg1));
    }

    @Override
    public Point getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
        return fromMysqlWkb(arg0.getBytes(arg1));
    }

    @Override
    public void setNonNullParameter(PreparedStatement arg0, int arg1, Point arg2, JdbcType arg3) throws SQLException {
        // TODO Auto-generated method stub

    }

    /*
     * bytes转GeoPoint对象
     */
    @SneakyThrows
    private Point fromMysqlWkb(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length - 4);
        ByteBuffer order = allocate.order(ByteOrder.LITTLE_ENDIAN);
        byte[] geomBytes = order.put(bytes, 4, bytes.length - 4).array();
        Geometry geometry = wkbReader.read(geomBytes);
        geometry.setSRID(srid);
        return (Point) geometry;
    }
}

