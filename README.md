# BuscaNomesArquivos
Mecanismo de Busca utilizando semáforos em Java

1 Definição e validação de comandos
Utilizando como base o arquivo "arquivosNomes.zip” crie um mecanismo
de busca em arquivos utilizando semáforos que limite a quantidade de uti-
lizadores em dois, somente duas Threads poder ̃ao ter acesso ao processo de
busca no arquivo. Você poderá lancar várias Threads para realizar a busca
por um determinado nome nos arquivos (busca aproximada), porém ao chamar 
o "serviço” de busca existe uma limitação de duas Threads por vez. A
saída no console deve mostrar todas as ocorrências de buscas válidas em cada
arquivo. A saída deve ser composta do nome do arquivo, número da linha
e o nome correspondente achado no arquivo. Façaa uma análise simples do
desempenho com uma thread em relação a duas threads.
Busca:
gl
Saída:
nomescompletos-00.txt - linha: 01 - Gladis Lopes
...
*Lembrar de usar ignoreCase na busca.
