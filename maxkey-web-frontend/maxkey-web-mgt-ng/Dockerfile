#MaxKey Mgt Frontend Docker Build

FROM node:latest

LABEL authors="MaxKey <support@maxsso.net>"

WORKDIR /usr/src/app

COPY package.json package.json

RUN npm config set registry https://registry.npm.taobao.org && npm i

COPY ./src  ./src

RUN npm install -g @angular/cli

RUN ng build --base-href /maxkey-mgt/

FROM nginx

COPY ./nginx.conf /etc/nginx/conf.d/default.conf

#RUN rm -rf /usr/share/nginx/html/*

COPY  dist/browser /usr/share/nginx/html/maxkey-mgt

#CMD ["nginx", "-g", "daemon off;"]

