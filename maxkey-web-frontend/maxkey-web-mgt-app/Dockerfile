FROM node:16.14.2

LABEL authors="MaxKey <maxkeysupport@163.com>"

WORKDIR /usr/src/app

COPY package.json package.json

RUN npm config set registry https://registry.npm.taobao.org \
    && npm i

COPY ./src  ./src
RUN npm install -g @angular/cli

RUN ng build --prod

FROM nginx

COPY ./nginx.conf /etc/nginx/conf.d/

RUN rm -rf /usr/share/nginx/html/*

COPY  dist /usr/share/nginx/html

#CMD ["nginx", "-g", "daemon off;"]

