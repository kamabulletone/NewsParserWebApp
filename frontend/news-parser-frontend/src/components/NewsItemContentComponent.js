import React from "react";

import myImage from 'C:/Users/Niga/Desktop/glinomesa/depression.png'
import myImage1 from 'C:/Users/Niga/Downloads/test.jpg'

let imgs = [
    "https://cdnn21.img.ria.ru/images/07e5/05/18/1733632342_0:8:1280:728_436x0_80_0_0_2a4c7a06cfb20a80a0b14a38ae89c3ec.jpg.webp",
    'C:/Users/Niga/Downloads/test.jpg'
  ];

function NewsItemContentComponent({news}) {

    

    return ( 
        news.map((record) => (
            <div key={record.id} className="Item">            
                <div className="title h5">
                    <p><a href={record.contentLink} className="list-item__title color-font-hover-only" target="_blank">{record.title}</a></p>
                </div>
                <div>
                    <div className="item_content">
                        <a href={record.contentLink} className="item_image" target="_blank">
                            <picture>
                                {record.pictures.map( (picture) => (
                                    <source key={picture.id} 
                                    media={picture.minWidth} 
                                    media-type={picture.mediaType}
                                    srcSet={picture.src}>
                                    </source>                                  
                                ) )}      
                                <img src="" alt={"Not Loading"}></img>
                            </picture>
                        </a>
                        
                    </div>
                    <div className="item_info">
                        <p>{record.createdOn.replace("T", " ").replaceAll("-", " ")}</p>
                    </div>
                </div>
        
            </div>
            )) 
            
    );
}

export default NewsItemContentComponent;