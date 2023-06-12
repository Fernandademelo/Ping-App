# Ping App

O Ping App é uma aplicação simples que realiza testes de ping em um determinado host (URL) e exibe o status de resposta HTTP. Ele é útil para verificar a disponibilidade de um servidor e monitorar a conectividade de rede.
<p align="center">
  <img src="https://github.com/Fernandademelo/Ping-App/assets/89407722/ecbc2d6e-98d5-4db8-8358-cb1280b6287e" alt="Exemplo do Ping App" width="400" height="300">
</p>

## Recursos

- Realiza testes de ping em um host específico.
- Exibe o status de resposta HTTP do host.
- Reproduz um som de alerta quando o host não está respondendo corretamente.
- Registra os resultados do ping em um log.
- Permite realizar o teste de ping manualmente com o clique de um botão.

## Tecnologias utilizadas

- Java
- Swing (biblioteca gráfica para criação da interface)
- HttpURLConnection (para realizar as requisições de ping)
- Java Sound API (para reprodução do som de alerta)

## Como usar

1. Clone o repositório para sua máquina local.
2. Certifique-se de ter o Java Development Kit (JDK) instalado.
3. Compile e execute o arquivo `PingApp.java`.
4. A aplicação será aberta em uma janela.
5. O status do ping será exibido na parte superior da janela.
6. Os resultados do ping serão registrados na área de log.
7. Para realizar um teste de ping manualmente, clique no botão "Pingar".

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests com melhorias, correções de bugs ou novos recursos.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
