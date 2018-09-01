package pappel

/**
 * Typed wrapper around the popular Express middleware router used in node.js
 * https://expressjs.com/en/api.html#router
 *
 * Tweaked from the work of Raphael Stäbler
 * https://medium.com/@raphaelstbler/how-i-wrote-a-full-stack-webapp-for-node-js-and-react-with-kotlin-bd18c45ee517
 * See from https://github.com/blazer82/pappel-framework
 */
data class Router(val expressRouter: dynamic)

