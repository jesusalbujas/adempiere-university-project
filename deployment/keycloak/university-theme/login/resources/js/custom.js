function notifyBackend(registrationUrl) {
  
  // obtengo el puerto de la API
  const apiPort = 1238
  const apiUrl = `http://api.adempiere.io:${apiPort}/db-listener`
  
   // Enviar una solicitud al backend para activar el db-listener
  async function app() {
    fetch(apiUrl, { method: "POST" })
    .catch((error) => console.error("Error al llamar a la API:", error.message));
  }
  app();

  // Redirige al formulario de registro
  window.location.href = registrationUrl;
}