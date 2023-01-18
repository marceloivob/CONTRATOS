#!/bin/bash

cd dist
rm -Rf conf/
mkdir conf
cp ../conf/* conf/
echo "Publicação no estaleiro. Ambiente $1 modulo maisbrasil-contratos-frontend"
estaleiro app deploy $1 --module "maisbrasil-contratos-frontend" --platform "frontend:nginx"
