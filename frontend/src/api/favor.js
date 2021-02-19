import { createInstance } from "./index.js";

const instance = createInstance();

function recommends(user_no, success, fail){
    instance
    .get(`/heritage/recommend/${user_no}`)
    .then(success)
    .catch(fail)
}

function ctcds(favor, success, fail){
    instance
    .post(`/heritage/ctcd`, JSON.stringify(favor))
    .then(success)
    .catch(fail)
}

function updateCtcds(favor, success, fail){
    instance
    .put(`/heritage/ctcd`, JSON.stringify(favor))
    .then(success)
    .catch(fail)
}

function bcodes(favor, success, fail){
    instance
    .put(`/heritage/bcode`, JSON.stringify(favor))
    .then(success)
    .catch(fail)
}

function ccces(favor, success, fail){
    instance
    .put(`/heritage/ccce`, JSON.stringify(favor))
    .then(success)
    .catch(fail)
}

function getFavor(user_no, success, fail){
    instance
    .get(`/heritage/${user_no}`)
    .then(success)
    .catch(fail)
}

function getImages(ccba_asno, ccba_ctcd_nm, ccba_kdcd, success, fail){
    instance
    .get(`/images?asno=${ccba_asno}&ctcd=${ccba_ctcd_nm}&kdcd=${ccba_kdcd}`)
    .then(success)
    .catch(fail)
}

function deleteFavor(user_no, success, fail){
    instance
    .delete(`/heritage/recommend/${user_no}`)
    .then(success)
    .catch(fail)
}

export { recommends, ctcds, updateCtcds, bcodes, ccces, getFavor, getImages, deleteFavor };