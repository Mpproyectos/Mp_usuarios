package kreandoapp.mpclientes.pojo;

import java.util.List;

import kreandoapp.mpclientes.db.entity.Ordenes;

public class Pedidos {
  private String retirasucursal;
  private String idrequest;
  private String pedido;
  private String nameprod;
  private String direccion;
  private String total;
  private String estado;
  private String fecha;
  private String hora;
  private String motivocancelacion;
  private String demora;
  private  String id;
  private String ubicacion;
  private String telefono;
  private String keykey;
  private String visto;
  private String envio;
  private String pagocontarjeta;
  private String cuponutilizado;
  private String promoutilizada;
  private String monto_cupon;
  private String zonaEnvio;
  private String cancelacon;
  private List <Ordenes> ordenes;


  public Pedidos() {
  }

  public Pedidos(String retirasucursal, String idrequest, String pedido, String nameprod, String direccion, String total, String estado, String fecha, String hora, String motivocancelacion, String demora, String id, String ubicacion, String telefono, String keykey, String visto, String envio, String pagocontarjeta, String cuponutilizado, String promoutilizada, String monto_cupon, String zonaEnvio, String cancelacon, List<Ordenes> ordenes) {
    this.retirasucursal = retirasucursal;
    this.idrequest = idrequest;
    this.pedido = pedido;
    this.nameprod = nameprod;
    this.direccion = direccion;
    this.total = total;
    this.estado = estado;
    this.fecha = fecha;
    this.hora = hora;
    this.motivocancelacion = motivocancelacion;
    this.demora = demora;
    this.id = id;
    this.ubicacion = ubicacion;
    this.telefono = telefono;
    this.keykey = keykey;
    this.visto = visto;
    this.envio = envio;
    this.pagocontarjeta = pagocontarjeta;
    this.cuponutilizado = cuponutilizado;
    this.promoutilizada = promoutilizada;
    this.monto_cupon = monto_cupon;
    this.zonaEnvio = zonaEnvio;
    this.cancelacon = cancelacon;
    this.ordenes = ordenes;
  }

  public String getRetirasucursal() {
    return retirasucursal;
  }

  public void setRetirasucursal(String retirasucursal) {
    this.retirasucursal = retirasucursal;
  }

  public String getIdrequest() {
    return idrequest;
  }

  public void setIdrequest(String idrequest) {
    this.idrequest = idrequest;
  }

  public String getPedido() {
    return pedido;
  }

  public void setPedido(String pedido) {
    this.pedido = pedido;
  }

  public String getNameprod() {
    return nameprod;
  }

  public void setNameprod(String nameprod) {
    this.nameprod = nameprod;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public String getHora() {
    return hora;
  }

  public void setHora(String hora) {
    this.hora = hora;
  }

  public String getMotivocancelacion() {
    return motivocancelacion;
  }

  public void setMotivocancelacion(String motivocancelacion) {
    this.motivocancelacion = motivocancelacion;
  }

  public String getDemora() {
    return demora;
  }

  public void setDemora(String demora) {
    this.demora = demora;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getKeykey() {
    return keykey;
  }

  public void setKeykey(String keykey) {
    this.keykey = keykey;
  }

  public String getVisto() {
    return visto;
  }

  public void setVisto(String visto) {
    this.visto = visto;
  }

  public String getEnvio() {
    return envio;
  }

  public void setEnvio(String envio) {
    this.envio = envio;
  }

  public String getPagocontarjeta() {
    return pagocontarjeta;
  }

  public void setPagocontarjeta(String pagocontarjeta) {
    this.pagocontarjeta = pagocontarjeta;
  }

  public String getCuponutilizado() {
    return cuponutilizado;
  }

  public void setCuponutilizado(String cuponutilizado) {
    this.cuponutilizado = cuponutilizado;
  }

  public String getPromoutilizada() {
    return promoutilizada;
  }

  public void setPromoutilizada(String promoutilizada) {
    this.promoutilizada = promoutilizada;
  }

  public String getMonto_cupon() {
    return monto_cupon;
  }

  public void setMonto_cupon(String monto_cupon) {
    this.monto_cupon = monto_cupon;
  }

  public String getZonaEnvio() {
    return zonaEnvio;
  }

  public void setZonaEnvio(String zonaEnvio) {
    this.zonaEnvio = zonaEnvio;
  }

  public String getCancelacon() {
    return cancelacon;
  }

  public void setCancelacon(String cancelacon) {
    this.cancelacon = cancelacon;
  }

  public List<Ordenes> getOrdenes() {
    return ordenes;
  }

  public void setOrdenes(List<Ordenes> ordenes) {
    this.ordenes = ordenes;
  }
}
