/**
 * 
 */
package sauce.agua.rest.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * @author daniel
 *
 */
public class Tool {
	public static BigDecimal rounding(BigDecimal number, Integer decimals) {
		if (decimals < 0) {
			Long factor = 1L;
			for (int ciclo = 0; ciclo > decimals; ciclo--, factor *= 10);
			return number.divide(new BigDecimal(factor), 0, RoundingMode.HALF_DOWN).multiply(new BigDecimal(factor));
		}
		return number.setScale(decimals, RoundingMode.HALF_DOWN);
	}
	
	public static OffsetDateTime hourAbsoluteArgentina() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -3);
		return calendar.getTime().toInstant().atOffset(ZoneOffset.UTC);
	}
	
	public static OffsetDateTime dateToOffsetDateTime(Date date) {
		return date.toInstant().atOffset(ZoneOffset.UTC);
	}

	public static BigDecimal interes(BigDecimal total, BigDecimal tasa, OffsetDateTime referencia,
			OffsetDateTime fecha_calculo) {
		Double tasa_diaria = Math.pow(1.0 + tasa.doubleValue(), 1.0 / 30.0) - 1;
		Period period = Period.between(referencia.toLocalDate(), fecha_calculo.toLocalDate());
		Double factor = Math.pow(1.0 + tasa_diaria, period.getDays()) - 1;
		Double interes_double = total.doubleValue() * factor;
		return new BigDecimal(interes_double);
	}

}
