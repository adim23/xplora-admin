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

import { getEntities } from './tour-content.reducer';

export const TourContent = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const tourContentList = useAppSelector(state => state.tourContent.entities);
  const loading = useAppSelector(state => state.tourContent.loading);
  const totalItems = useAppSelector(state => state.tourContent.totalItems);

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
      <h2 id="tour-content-heading" data-cy="TourContentHeading">
        <Translate contentKey="xploraAdminApp.tourContent.home.title">Tour Contents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="xploraAdminApp.tourContent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tour-content/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xploraAdminApp.tourContent.home.createLabel">Create new Tour Content</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tourContentList && tourContentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="xploraAdminApp.tourContent.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="xploraAdminApp.tourContent.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="xploraAdminApp.tourContent.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('shortDescription')}>
                  <Translate contentKey="xploraAdminApp.tourContent.shortDescription">Short Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shortDescription')} />
                </th>
                <th className="hand" onClick={sort('data')}>
                  <Translate contentKey="xploraAdminApp.tourContent.data">Data</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('data')} />
                </th>
                <th className="hand" onClick={sort('meta')}>
                  <Translate contentKey="xploraAdminApp.tourContent.meta">Meta</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('meta')} />
                </th>
                <th className="hand" onClick={sort('cancellation')}>
                  <Translate contentKey="xploraAdminApp.tourContent.cancellation">Cancellation</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cancellation')} />
                </th>
                <th className="hand" onClick={sort('activityPath')}>
                  <Translate contentKey="xploraAdminApp.tourContent.activityPath">Activity Path</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('activityPath')} />
                </th>
                <th className="hand" onClick={sort('atAGlance')}>
                  <Translate contentKey="xploraAdminApp.tourContent.atAGlance">At A Glance</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('atAGlance')} />
                </th>
                <th className="hand" onClick={sort('inDetail')}>
                  <Translate contentKey="xploraAdminApp.tourContent.inDetail">In Detail</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('inDetail')} />
                </th>
                <th className="hand" onClick={sort('whatIsIncluded')}>
                  <Translate contentKey="xploraAdminApp.tourContent.whatIsIncluded">What Is Included</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('whatIsIncluded')} />
                </th>
                <th className="hand" onClick={sort('youCanAdd')}>
                  <Translate contentKey="xploraAdminApp.tourContent.youCanAdd">You Can Add</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('youCanAdd')} />
                </th>
                <th className="hand" onClick={sort('importantInformation')}>
                  <Translate contentKey="xploraAdminApp.tourContent.importantInformation">Important Information</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('importantInformation')} />
                </th>
                <th className="hand" onClick={sort('extraInfo')}>
                  <Translate contentKey="xploraAdminApp.tourContent.extraInfo">Extra Info</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('extraInfo')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="xploraAdminApp.tourContent.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tourContent.language">Language</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tourContentList.map((tourContent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tour-content/${tourContent.id}`} color="link" size="sm">
                      {tourContent.id}
                    </Button>
                  </td>
                  <td>{tourContent.code}</td>
                  <td>{tourContent.title}</td>
                  <td>{tourContent.shortDescription}</td>
                  <td>{tourContent.data}</td>
                  <td>{tourContent.meta}</td>
                  <td>{tourContent.cancellation}</td>
                  <td>{tourContent.activityPath}</td>
                  <td>{tourContent.atAGlance}</td>
                  <td>{tourContent.inDetail}</td>
                  <td>{tourContent.whatIsIncluded}</td>
                  <td>{tourContent.youCanAdd}</td>
                  <td>{tourContent.importantInformation}</td>
                  <td>{tourContent.extraInfo}</td>
                  <td>
                    {tourContent.createdDate ? (
                      <TextFormat type="date" value={tourContent.createdDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {tourContent.language ? <Link to={`/language/${tourContent.language.id}`}>{tourContent.language.code}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tour-content/${tourContent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tour-content/${tourContent.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/tour-content/${tourContent.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="xploraAdminApp.tourContent.home.notFound">No Tour Contents found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={tourContentList && tourContentList.length > 0 ? '' : 'd-none'}>
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

export default TourContent;
