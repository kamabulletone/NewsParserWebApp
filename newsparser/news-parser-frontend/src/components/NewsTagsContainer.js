
import axios from 'axios';
import React, { useState, useEffect } from 'react';
import NewsTagsItemComponent from "./NewsTagsItemComponent";

const NewsTagsContainer = () => {

    
    const [tag, setTag] = useState(0);

    const [tagsData,setTagsData]=useState([])

    // Аналогично componentDidMount и componentDidUpdate:
    useEffect(() => {
        axios.get("/api/v1/news/tags")
        .then(res=>{
            console.log(res.data);
            setTagsData(res.data)
          })
          .catch(err=>{
            console.log(err);
          })
    }, []);


    const tags=tagsData.map((data,id)=>{
        return <NewsTagsItemComponent key={id} name={data}/>
      })

  
    const mystyle = {
        color: "dark",
        backgroundColor: "dark",
        padding: "10px",
        fontFamily: "Arial"
      };



  return (

    <div >
        <div style={{backgroundColor: 'darkred', border: "1px solid darkblue", color: 'white', marginBottom: '5%'}} 
        className="row p-3 justify-content-between">
            {/* col-xs-1 col-sm-2 col-md-4 col-lg-2 */}
            {tags}

        </div>
        
    </div>
    
  )
}
export default NewsTagsContainer