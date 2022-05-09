import React from "react";

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