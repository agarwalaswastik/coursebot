import {FormEventHandler, useState} from "react";

const API_URL = "http://localhost:8080/api";

export default function SubmitArticle() {
    const [linkUrl, setLinkUrl] = useState<string>("");
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit: FormEventHandler<HTMLFormElement> = async (e) => {
        e.preventDefault();

        setIsLoading(true);
        const response = await fetch(API_URL + "/article", {
            method: "POST",
            body: linkUrl,
        });

        if (response.ok) alert("Article Submitted!");
        else alert("Error Occurred");

        setIsLoading(false);
        setLinkUrl("");
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-8 items-center">
            <input
                className="text-4xl p-4 rounded-xl outline-none bg-[#222222] text-white w-[75vw] border-2 border-slate-600 focus:border-emerald-600 transition-colors"
                id="link_url" name="link_url" placeholder="Enter medium link" value={linkUrl}
                onChange={(e) => setLinkUrl(e.target.value)} disabled={isLoading} required/>
            <input type="submit"
                   className="text-3xl hover:cursor-pointer px-4 py-2 border-2 border-emerald-400 rounded-xl w-fit text-emerald-400 transition-colors hover:bg-emerald-400 hover:text-[#222222]"
                   disabled={isLoading}
            />
        </form>
    );
}