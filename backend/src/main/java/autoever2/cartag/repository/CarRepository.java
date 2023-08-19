package autoever2.cartag.repository;

import autoever2.cartag.domain.car.CarDefaultDto;
import autoever2.cartag.domain.car.CarDefaultInfoDto;
import autoever2.cartag.domain.car.CarInfoDto;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private final NamedParameterJdbcTemplate template;

    public CarRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<CarInfoDto> findCarByCarType(int carType) {
        String sql = "select car_id, trim, car_default_price, outer_image, inner_image, wheel_image, " +
                "car_description from Car where car_type_id = :carType";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("carType", carType);
        return template.query(sql, param, CarRowMapper());

    }

    private RowMapper<CarInfoDto> CarRowMapper() {
        return BeanPropertyRowMapper.newInstance(CarInfoDto.class);
    }

    public Optional<Long> findCarBoughtCountByCarId(int carId) {
        String sql = "select bought_count from Car where car_id = :carId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("carId", carId);

        return Optional.ofNullable(DataAccessUtils.singleResult(template.query(sql, param, (rs, rowNum) -> rs.getLong("bought_count"))));
    }

    public Optional<CarDefaultInfoDto> findCarDefaultByCarId(int carId) {
        String sql = "select car_type_name, trim, car_default_price " +
                "from Car as c inner join CarType as ct " +
                "on c.car_type_id = ct.car_type_id where c.car_id = :carId";
        try {
            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("carId", carId);
            return Optional.of(template.queryForObject(sql, param, carDefaultRowMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<CarDefaultInfoDto> carDefaultRowMapper(){
        return (rs, rowNum) ->
                CarDefaultInfoDto
                        .builder()
                        .carType(rs.getString("car_type_name"))
                        .trim(rs.getString("trim"))
                        .carDefaultPrice(rs.getInt("car_default_price"))
                        .build();
    }


}
