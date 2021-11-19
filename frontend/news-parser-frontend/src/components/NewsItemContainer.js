import React, { useState, useEffect, useRef } from 'react';
import TagFormComponent from './TagFormComponent'
import NewsItemContentComponent from './NewsItemContentComponent';
import axios from 'axios';
import { format } from "date-fns";

function NewsItemContainer() {

    const [newsData,setNewsData]=useState([])
    const [newsRequestProps,setRequestsProps]=useState({})
    const newsRequestPropsRef = useRef();

    newsRequestPropsRef.current = newsRequestProps;


    useEffect(() => {
      //customGetNewsHandler();
      console.log("request props: " + newsRequestPropsRef.current)
      renderNewsRequest(newsRequestProps);
  }, [newsRequestProps]);

    function renderNewsRequest() {
      if (newsRequestProps === undefined) {
        return setNewsData(undefined)
      }
      console.log(newsRequestProps)
      console.log(newsRequestProps.createdFrom)
      console.log(newsRequestProps.createdTo)
      console.log(newsRequestProps.tag)
      console.log(newsRequestProps.page)
      axios.get("/api/v1/news/",{ params: 
        {
          createdFrom: newsRequestProps.createdFrom,
          createdTo: newsRequestProps.createdTo,
          page: newsRequestProps.page,
          tag: newsRequestProps.tag                                    
        }
          })
          .then(res=>{
          console.log(res.data)
          // console.log(res.data.news);
          // setTagsData(res.data)
          setNewsData(res.data.news);
          })
          .catch(err=>{
          console.log(err);
          })
          
    }
    function customGetNewsHandler(dict) {
        // console.log("amogus");
        let date1 = format(dict.createdFrom, "yyyy-MM-dd HH:mm")
        let date2 = format(dict.createdTo, "yyyy-MM-dd HH:mm")
        let page_count = dict.page
        let tag_name = dict.tag
        // console.log(date1)
        // console.log(date2)
        // console.log(page_count)
        // console.log(tag_name)
        let temp_dict = {
          "createdFrom" : date1,
          "createdTo" : date2,
          "tag" : tag_name,
          "page" : page_count
      }
        setRequestsProps(temp_dict)
    }


    return (
        <div className="col-xs-1 col-sm-2 col-md-4 col-lg-3">
            <TagFormComponent onNewsGetHandler={customGetNewsHandler}/>
            <NewsItemContentComponent news={newsData}/>
        </div>
    );


}

export default NewsItemContainer;