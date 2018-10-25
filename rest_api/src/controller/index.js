import express from 'express';
import Crawler from 'crawler';
import extractDomain from 'extract-domain';
import validUrl from 'valid-url';
import cheerio from 'cheerio';
import fs from 'fs';

let route = express();

let nodeArray=[];
let baseDomain ='';
let fileName = '';
/*
 * this handles /v1/route/get route to invoking web crawling
*/
route.get('/get',(req,res)=>{

console.log(`inside employee controller request inputs: ${req.query.url}`);

baseDomain = extractDomain(req.query.url);
fileName = req.query.fileName;
 c.queue(req.query.url);

 /*checking for drain queue
  *if there is no more url present for crawling then it will invoked
  */
 c.on('drain',function(){

     fs.writeFile(fileName, JSON.stringify(nodeArray), function (err) {
        if (err) throw err;

          console.log('crawling is done');
      });

 });
  res.send('Response returned back successfully');
});

/*Crawler libray is used to crawl the hyperlinks
  *in this example queue function is recursively called to fullfill our requirement
  */
var c = new Crawler({
    maxConnections : 100,

    callback : function (error, res, done) {
        if(error){
            console.log(error);
        }else{
            var $ =  cheerio.load(res.body);
            var urls = $('a');
            urls.each(function(i,a){
              var href = a.attribs.href;
                  if (href) {
                      /*invalid URL check
                      */
                      if (validUrl.isUri(href) && (nodeArray.indexOf(href) =='-1') && (href.indexOf('mailto:')=='-1') && (href.indexOf('javascript:')=='-1')){
                        console.log(res.request.host + ' > ' + href);

                      nodeArray.push(href);
                      /*restriction to be in basedomain
                      */
                      if(href.indexOf(baseDomain)!= -1) {
                        /*follow child URL using
                        *recursive call to queue
                        */
                          c.queue(href);
                            }
                          }
                        }
                });
        }
        done();
    }
});


export default route;
