#!/bin/bash
echo "digite o caminho absoluto onde se encontra aplicação RMI: Ex: /home/usuario/aplicacaoRMI/RMISimples/"
read caminho
rmiregistry -J-Djava.rmi.server.codebase=file://$caminho
