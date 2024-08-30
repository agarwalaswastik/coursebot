import {FormEventHandler, useState} from "react";
import {ArticleAttributes} from "./ViewArticles.tsx";
import Article from "../components/Article.tsx";

const API_URL = "http://localhost:8080/api";

export default function CreateCourse() {
    const [prompt, setPrompt] = useState<string>("");
    const [isLoading, setIsLoading] = useState(false);
    const [data, setData] = useState<ArticleAttributes[]>([]);

    const handleSubmit: FormEventHandler<HTMLFormElement> = async (e) => {
        e.preventDefault();

        setIsLoading(true);
        const response = await fetch(API_URL + "/course?" + new URLSearchParams({
            prompt
        }).toString());

        const responseBody = await response.json() as ArticleAttributes[];

        setIsLoading(false);
        setData(responseBody);
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-8 items-center">
            <textarea
                className="resize-none outline-none p-2 rounded-xl text-lg bg-[#222222] text-slate-200 w-[75vw] h-40 border-2 border-slate-600"
                placeholder="Enter your prompt..."
                value={prompt}
                onChange={(e) => setPrompt(e.target.value)}
            ></textarea>
            <input type="submit"
                   className="text-3xl hover:cursor-pointer px-4 py-2 border-2 border-emerald-400 rounded-xl w-fit text-emerald-400 transition-colors hover:bg-emerald-400 hover:text-[#222222]"
                   disabled={isLoading}
            />
            {isLoading && <p className="text-slate-200">Loading...</p>}
            <section className="grid gap-6 m-8 md:grid-cols-2">
                {data.map(elem => {
                    return <Article key={elem.articleId} {...elem}/>;
                })}
            </section>
        </form>
);
}