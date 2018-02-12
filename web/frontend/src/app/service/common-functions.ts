export function getBaseLocation() {
   
    let basePath: string = location.pathname.split('/')[1] || '';
    console.log("basepath - " +  basePath);
    return '/' + basePath;
}