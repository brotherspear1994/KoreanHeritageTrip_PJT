import { createInstance } from "./index.js";

const instance = createInstance();

function heritages(success, fail){
    instance
    .get('/heritages/ /')
    .then(success)
    .catch(fail)
}

export { heritages, };