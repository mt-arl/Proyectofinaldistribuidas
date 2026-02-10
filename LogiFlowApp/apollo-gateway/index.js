const { ApolloServer } = require('@apollo/server');
const { startStandaloneServer } = require('@apollo/server/standalone');

const typeDefs = `#graphql
  type Vehiculo {
    id: ID
    placa: String
    modelo: String
    estado: String
  }
  type Pedido {
    id: ID
    customerName: String
    status: String
  }
  type Query {
    pedidos: [Pedido]
    flota: [Vehiculo] # Consulta para el Dashboard de la Fase 2
  }
`;

const resolvers = {
  Query: {
    pedidos: async () => {
      const res = await fetch('http://pedido-service:8084/api/orders');
      return res.json();
    },
    flota: async () => {
      const res = await fetch('http://fleet-service:8083/api/flota');
      return res.json();
    },
  },
};

const server = new ApolloServer({ typeDefs, resolvers });
startStandaloneServer(server, { listen: { port: 4000 } }).then(({ url }) => {
  console.log(`🚀 Gateway listo en: ${url}`);
});