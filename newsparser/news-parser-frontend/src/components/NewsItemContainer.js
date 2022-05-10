import React, { useState, useEffect, useRef } from 'react';
import TagFormComponent from './TagFormComponent'
import NewsItemContentComponent from './NewsItemContentComponent';
import axios from 'axios';
import { format } from "date-fns";

function NewsItemContainer() {

    const [newsData,setNewsData]=useState([])
    const [newsRequestProps,setRequestsProps]=useState({})
    const [initialLoading,setInitialLoading]=useState(false)
    const newsRequestPropsRef = useRef();
    const [totalPages,setTotalPages]=useState(1)
    const [currentPage,setCurrentPage]=useState(0)

    newsRequestPropsRef.current = newsRequestProps;


    useEffect(() => {
      console.log("request props: " + newsRequestPropsRef.current)
      renderNewsRequest(newsRequestProps);
  }, [newsRequestProps,initialLoading,totalPages,currentPage]);

    function renderNewsRequest() {
      if (Object.values(newsRequestProps).every(x => x === null || x === undefined)) {
        return;
      }
      console.log(newsRequestProps)
      console.log(newsRequestProps.createdFrom)
      console.log(newsRequestProps.createdTo)
      console.log(newsRequestProps.tag)
      console.log(newsRequestProps.page)
      console.log(totalPages)
      console.log(currentPage)
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
          console.log(res.data.totalPages)
          console.log(res.data.currentPage)
          setNewsData(res.data.news);
          setTotalPages(res.data.totalPages)
          setCurrentPage(res.data.currentPage)
          })
          .catch(err=>{
          console.log(err);
          })
          
    }
    function customGetNewsHandler(dict) {
        let date1 = format(dict.createdFrom, "yyyy-MM-dd HH:mm")
        let date2 = format(dict.createdTo, "yyyy-MM-dd HH:mm")
        let page_count = dict.page
        let tag_name = dict.tag
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
            <TagFormComponent onNewsGetHandler={customGetNewsHandler} currentPage={currentPage} totalPages={totalPages}/>
            <NewsItemContentComponent news={newsData}/>
        </div>
    );


}

export default NewsItemContainer;