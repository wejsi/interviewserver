"# interviewserver" 

Tecnologias utilizadas:

    Apache Maven 3.5.0 
    
    Java 8

    Eclipse equinox com plugin maven.

Arquitetura:
    
    Springboot + JPA +Hibernate + Tomcat + Hsqldb 

Instruções:
    
    1. Na raiz do projeto executar o comando mvn install para obter as dependências necessárias.
   
    2. Importar o projeto no eclipse como um projeto maven.
    
    3. Iniciar a conexão com o banco de dados executando hsqldb.bat
   
    4. Localizar e executar a classe Main src/main/java/org/interview/config/Application.java
    
    5. A autorização básica para os endpoints é usuário:admin senha:admin.
    
    6. Existe uma carga de colaboradores consulta tabela PUBLIC.COL_COLABORADOR.
    
    
URLs:
    user=admin
    password=admin
    id=COL_ID

Obtém colaborador:
     GET
     Http://localhost:8080/api/ponto/colaborador/{id}


Obtém colaboradores.
     GET
     Http:/localhost:8080/api/ponto/colaboradores

Registrar ponto do colaborador.
     PUT
     Http://localhost:8080/api/ponto/colaborador/{id}/registrar

Obter horas diárias do colaborador.
     GET
     Http://localhost:8080/api/ponto/colaborador/{id}/total-horas-dia

Obter horas mensal do colaborador.
     GET
     Http://localhost:8080/api/ponto/colaborador/{id}/total-horas-mes

Obtém eventos diário do colaborador
    GET
    Http://localhost:8080/api/ponto/eventos/colaborador/{id}/diario

Obtém eventos mensal do colaborador
    GET
    Http://localhost:8080/api/ponto/eventos/colaborador/{id}/mensal











    
  
  



    
