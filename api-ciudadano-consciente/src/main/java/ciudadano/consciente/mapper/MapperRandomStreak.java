package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateRandomStreak;
import ciudadano.consciente.dto.DTORandomStreak;
import ciudadano.consciente.model.RandomStreak;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapperRandomStreak {

    List<DTORandomStreak> entityToDto(List<RandomStreak> allRandomStreaks);

    DTORandomStreak entityToDto(RandomStreak randomStreak);

}
