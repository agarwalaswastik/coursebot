import {useEffect, useState} from "react";
import Article from "../components/Article.tsx";

const API_URL = "http://localhost:8080/api";

export type ArticleAttributes = {
    articleId: number,
    linkUrl: string,
    summary: string,
    updated: number[]
};

export default function ViewArticles() {
    const [isLoading, setIsLoading] = useState(true);
    const [data, setData] = useState<ArticleAttributes[]>([]);

    useEffect(() => {
        const getArticles = async () => {
            const response = await fetch(API_URL + "/article", {
                method: "GET"
            });

            const responseBody = await response.json() as ArticleAttributes[];

            console.log(responseBody);

            setIsLoading(false);
            setData(responseBody);
        };

        getArticles();
    }, []);

    return (
        <section className="grid gap-6 m-8 md:grid-cols-2">
            {isLoading && <p className="text-slate-200">Loading...</p>}
            {data.map(elem => {
                return <Article key={elem.articleId} {...elem}/>;
            })}
        </section>
    );
}