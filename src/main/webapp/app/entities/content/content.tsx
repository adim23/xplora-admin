import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './content.reducer';

export const Content = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const contentList = useAppSelector(state => state.content.entities);
  const loading = useAppSelector(state => state.content.loading);
  const totalItems = useAppSelector(state => state.content.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="content-heading" data-cy="ContentHeading">
        <Translate contentKey="xploraAdminApp.content.home.title">Contents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="xploraAdminApp.content.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/content/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xploraAdminApp.content.home.createLabel">Create new Content</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contentList && contentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="xploraAdminApp.content.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="xploraAdminApp.content.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="xploraAdminApp.content.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('shortDescription')}>
                  <Translate contentKey="xploraAdminApp.content.shortDescription">Short Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shortDescription')} />
                </th>
                <th className="hand" onClick={sort('data')}>
                  <Translate contentKey="xploraAdminApp.content.data">Data</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('data')} />
                </th>
                <th className="hand" onClick={sort('meta')}>
                  <Translate contentKey="xploraAdminApp.content.meta">Meta</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('meta')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="xploraAdminApp.content.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.language">Language</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.destination">Destination</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tourExtraInfo">Tour Extra Info</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tour">Tour</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tourCategory">Tour Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.place">Place</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.placeCategory">Place Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tourExtraCategory">Tour Extra Category</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tourExtra">Tour Extra</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.menu">Menu</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.webPage">Web Page</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tag">Tag</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.tourStep">Tour Step</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.promotion">Promotion</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.content.imageFile">Image File</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contentList.map((content, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/content/${content.id}`} color="link" size="sm">
                      {content.id}
                    </Button>
                  </td>
                  <td>{content.code}</td>
                  <td>{content.title}</td>
                  <td>{content.shortDescription}</td>
                  <td>{content.data}</td>
                  <td>{content.meta}</td>
                  <td>
                    {content.createdDate ? <TextFormat type="date" value={content.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{content.language ? <Link to={`/language/${content.language.id}`}>{content.language.code}</Link> : ''}</td>
                  <td>{content.createdBy ? content.createdBy.login : ''}</td>
                  <td>
                    {content.destination ? <Link to={`/destination/${content.destination.id}`}>{content.destination.code}</Link> : ''}
                  </td>
                  <td>{content.tourExtraInfo ? <Link to={`/tour/${content.tourExtraInfo.id}`}>{content.tourExtraInfo.code}</Link> : ''}</td>
                  <td>{content.tour ? <Link to={`/tour/${content.tour.id}`}>{content.tour.code}</Link> : ''}</td>
                  <td>
                    {content.tourCategory ? <Link to={`/tour-category/${content.tourCategory.id}`}>{content.tourCategory.code}</Link> : ''}
                  </td>
                  <td>{content.place ? <Link to={`/place/${content.place.id}`}>{content.place.code}</Link> : ''}</td>
                  <td>
                    {content.placeCategory ? (
                      <Link to={`/place-category/${content.placeCategory.id}`}>{content.placeCategory.code}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {content.tourExtraCategory ? (
                      <Link to={`/tour-extra-category/${content.tourExtraCategory.id}`}>{content.tourExtraCategory.code}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{content.tourExtra ? <Link to={`/tour-extra/${content.tourExtra.id}`}>{content.tourExtra.code}</Link> : ''}</td>
                  <td>{content.menu ? <Link to={`/menu/${content.menu.id}`}>{content.menu.code}</Link> : ''}</td>
                  <td>{content.webPage ? <Link to={`/web-page/${content.webPage.id}`}>{content.webPage.code}</Link> : ''}</td>
                  <td>{content.tag ? <Link to={`/tag/${content.tag.id}`}>{content.tag.code}</Link> : ''}</td>
                  <td>{content.tourStep ? <Link to={`/tour-step/${content.tourStep.id}`}>{content.tourStep.code}</Link> : ''}</td>
                  <td>{content.promotion ? <Link to={`/promotion/${content.promotion.id}`}>{content.promotion.code}</Link> : ''}</td>
                  <td>{content.imageFile ? <Link to={`/image-file/${content.imageFile.id}`}>{content.imageFile.code}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/content/${content.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/content/${content.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/content/${content.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="xploraAdminApp.content.home.notFound">No Contents found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={contentList && contentList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Content;
