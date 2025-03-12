Guia de Configuração e Execução do Projeto
A - Configurações no HubSpot
1. Criar Conta de Desenvolvedor
Crie uma conta de desenvolvedor no HubSpot para gerenciar aplicativos e integrações.

2. Criar Conta de Teste
Após criar a conta de desenvolvedor, crie uma conta de teste para validar a integração.

3. Criar Aplicativo
Utilize a conta de desenvolvedor para criar um aplicativo no HubSpot.

3.1. Configurar URL de Redirecionamento
No aplicativo criado, defina a URL de redirecionamento como:

http://localhost:8080/oauth/callback
3.2. Adicionar Escopos
Adicione os seguintes escopos ao aplicativo:

crm.objects.contacts.read
crm.objects.contacts.write
4. Configuração do Webhook
4.1. Instalar NGROK (Opcional)
Se desejar expor seu ambiente local para receber webhooks, utilize o ngrok.

4.2. Configurar Webhook no HubSpot
No aplicativo, vá até a aba Webhooks.
Adicione a URL de destino do servidor seguida de /webhooks/contact-created.
Caso esteja rodando localmente, utilize o ngrok para expor a URL.
4.3. Criar Assinatura de Eventos
No HubSpot, clique em Criar Assinatura.
Selecione o objeto Contato e o evento Criado.
B - Configuração do Projeto
1. Configurar o arquivo application.properties
Edite o arquivo application.properties com as credenciais do HubSpot:

hubspot.client-id=SEU_CLIENT_ID
hubspot.client-secret=SEU_CLIENT_SECRET
hubspot.redirect-uri=http://localhost:8080/oauth/callback
hubspot.scope=crm.objects.contacts.write%20crm.objects.contacts.read
C - Utilizando a API
1. Executar o Projeto
Baixe o projeto do Git e execute a aplicação:

Abra a classe ApiApplication.
Clique com o botão direito > Run As > Java Application.
2. Gerar a URL de Autorização
Realize uma requisição GET para o endpoint:

http://localhost:PORTA_CONFIGURADA/oauth/authorize
Exemplo: http://localhost:8080/oauth/authorize

Após ser redirecionado para a página do HubSpot e autorizar, o access_token será exibido no console do Eclipse.

3. Criar um Contato
Faça uma requisição POST para criar um contato:

http://localhost:PORTA_CONFIGURADA/contacts/create
Exemplo: http://localhost:8080/contacts/create

Cabeçalhos da Requisição
No header, envie:

makefile
Copiar
Editar
Authorization: Bearer SEU_ACCESS_TOKEN
O token access_token é obtido no console após o processo de autorização.

Corpo da Requisição
Envie o seguinte JSON:

{
	"email": "testeEmail123999@example.com",
	"firstname": "teste_firstname",
	"lastname": "teste_lastname",
	"phone": "555-555-5555"
}
4. Verificar o Retorno do Webhook
Após criar o contato, o webhook será acionado.
Para verificar o retorno, consulte o console do projeto.


Documentação Técnica

Este é um projeto simples em Spring, no qual apliquei o Transaction Script Pattern, centralizando a camada de serviço para autenticação. Não apliquei qualquer tipo de persistência de dados, nem utilizei bibliotecas externas. O foco foi na simplicidade, organização dos métodos e das classes.

Separei o processamento do webhook aplicando o conceito de component e criei um tratamento genérico para exceções. Além disso, apliquei validação de rate limit apenas para o processo de criação de contatos.

Considerações para Evolução do Código
Acredito que, para a evolução do código, será necessário separar o processamento nas camadas, garantindo que cada uma tenha sua implementação própria e trate suas respectivas regras. Também sugiro criar controladores mais segmentados para lidar com diferentes tipos de clientes, com um controlador para cada tipo, distribuindo os endpoints adequadamente.

Considerações Finais
Tentei aplicar fortemente o princípio de Responsabilidade Única. Meu foco foi entregar o melhor que pude, considerando os testes, revisões de código e a nomenclatura, dentro do tempo disponível.
