package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoNivel;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.transferible.TransferibleNivel;
import ciudadano.consciente.transferible.TransferibleRequestCrearNivel;
import ciudadano.consciente.transformador.TransformadorNivel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServicioNivel {

    @Inject
    AccesoNivel accesoNivel;

    @Inject
    TransformadorNivel transformadorNivel;

    public TransferibleNivel obtener(Integer identificador) {

        Nivel nivel = accesoNivel.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe"));

        return  transformadorNivel.entidadATransferible(nivel);

    }

    public List<TransferibleNivel> obtenerTodos() {

        return transformadorNivel.entidadATransferible(accesoNivel.obtenerTodos());

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accesoNivel.eliminar(identificador)) {
            throw new HttpNoContentException("Organización a eliminar no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleNivel crear(TransferibleRequestCrearNivel transferibleRequestCrearNivel) {

        Nivel nivel = transformadorNivel.transferibleAEntidad(transferibleRequestCrearNivel);

        nivel = accesoNivel.persistir(nivel)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir Nivel."));

        return transformadorNivel.entidadATransferible(nivel);

    }
}
