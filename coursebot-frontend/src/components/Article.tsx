import {ArticleAttributes} from "../feature/ViewArticles.tsx";

export default function Article({linkUrl, summary, updated}: ArticleAttributes) {
    return (
        <article className="rounded-xl p-4 text-slate-200 flex flex-col justify-between gap-2 border-2 border-slate-600">
            <p>{summary}</p>
            <div className="flex justify-between">
                <a className="text-emerald-400 font-semibold" href={linkUrl} target="_blank" rel="nofollow noreferrer">Go
                    to article</a>
                <p>Added - {updated[0]}/{updated[1]}/{updated[2]}</p>
            </div>
        </article>
    );
}