FROM node:alpine

WORKDIR /angryfish-react-frontend

ENV PATH /angryfish-react-frontend/node_modules/.bin:$PATH

COPY package.json ./

COPY package-lock.json ./

RUN npm i

COPY . ./

CMD ["npm", "start"]