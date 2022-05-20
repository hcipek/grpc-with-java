const grpc = require('grpc')
const protoLoader = require('@grpc/proto-loader')

const packageDef = protoLoader.loadSync('proto/bank-service.proto')
const protoDesc = grpc.loadPackageDefinition(packageDef)

const client = new protoDesc.BankService('localhost:8900', grpc.credentials.createInsecure())

//SUCCESS CASE
client.getBalance({accountNumber: 8}, (err, balance) => {
    if (err) {
        console.error('oopsie')
    } else {
        console.log(balance)
    }
})

//ERROR CASE
client.getBalance({accountNumber: 2321}, (err, balance) => {
    if (err) {
        console.error('oopsie')
    } else {
        console.log(balance)
    }
})
