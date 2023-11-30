package ciudadano.consciente.utilidad;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UtilidadCamposRequest {

    public boolean isCampoValido(Integer campo) {
        return campo != null;
    }

    public boolean isCampoValido(String campo) {
        return campo != null &&  !"null".equals(campo) && !campo.trim().isEmpty();
    }

}
